package swingserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket; //класс реализует серверный сокет, который ожидает запросы, приходящие от клиентов по сети, и может отправлять ответ
import java.net.Socket; //класс реализует клиентский сокет

public class Server_3 {
    public static void main(String[] args) throws IOException {
        //Создаем строку для списка всех файлов и папок, лежащих по указанному пути
        String allNameForImg = "";
        //Создаем файл-каталог
        File dir = null;
        //Создаем строку для указания пути
        String mainWay="";
        //Создаем экземпляр сервера serverSocket
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){ //
            System.out.println();
            //Создаем входящего в серврер клиента socket
            Socket socket = serverSocket.accept();

            if (!socket.isClosed()){
                //Если клиент запущен, создаем соответсвующий входной поток данных
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                //Создаем ключ из полученных данных входного потока
                String key = dataInputStream.readUTF();
                System.out.println(key);
                if (key.equals("Открыть директорию")) {
                    //Если была нажата кнопка "Открыть директорию" (отражено в ключе), то инициализируем файл-каталог dir, обозначив путь "directory"
                    dir = new File("directory");
                    //Указываем строку-путь при нажатии на кнопку "Открыть директорию"
                    mainWay="directory";
                    allNameForImg = "";
                    //В строку allNameForImg запишем через запятую все файлы, находящиеся в "directory"
                    for (File file : dir.listFiles()) {
                        allNameForImg = allNameForImg + file.getName() + ',';
                    }
                    //Уберем последнюю запятую в строке
                    allNameForImg = allNameForImg.substring(0, allNameForImg.length() - 1);

                    System.out.println(allNameForImg);
                    //Создаем выходной поток для клиента socket и записываем в него полное имя файла
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(allNameForImg);
                    dataOutputStream.flush();
                }
                else{
                    //Обрабатываем вид пути к нашему местоположению внутри директории
                    if (!key.contains(".png") && !key.contains(".jpg") && !key.contains(".bmp") && !key.contains(".pds") && !key.contains(".gif") && !key.contains(".tif"))
                    //Если нажат не графический файл
                    {
                        //Если нажата кнопка "Back" и путь НЕ состоит только из "directory", то переписываем путь mainway на папку назад
                        if (key.equals("Back")) {
                            if(!mainWay.equals("directory")){
                                int z = mainWay.lastIndexOf("/");
                                mainWay = mainWay.substring(0, z);
                            }
                        }
                        //Если у нас открыт какой-либо файл, то переписываем соответствующе путь mainway с файлом
                        else if (!mainWay.matches("[/]" + key + ".{1,}") && mainWay.contains(key)) {
                            mainWay = mainWay.substring(0, mainWay.length() - key.length() - 1);
                        }
                        //Иначе увеличиваем путь, добавив название нажатого элемента
                        else{
                            mainWay = mainWay + "/" + key;
                        }
                        System.out.println(mainWay);
                        //Создаем новый файл с путем из mainway
                        File newFile = new File(mainWay);
                        //Очищаем строку с содержимым по указанному пути
                        allNameForImg = "";
                        //Файлы по указанного в mainway пути записываем в строку allNameForImg
                        for (File file : newFile.listFiles()) {
                            allNameForImg = allNameForImg + file.getName() + ',';
                        }
                        //Если строка осталась пустой (файлов внутри не нашлось)
                        if (allNameForImg.equals("")) {
                            //Создаем выходной поток со значением выходного потока клиента socket и указываем, что все пусто
                            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                            dataOutputStream.writeUTF("<Empty>");
                            dataOutputStream.flush();
                        }
                        //Если строка не осталась пустой (файлы внутри есть)
                        else{
                            //Убираем последнюю запятую из allNameForImg, создаем выходной поток и записываем в него файлы
                            allNameForImg = allNameForImg.substring(0, allNameForImg.length() - 1);
                            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                            dataOutputStream.writeUTF(allNameForImg);
                            dataOutputStream.flush();
                        }
                    }
                    //Иначе, если нажат графический файл
                    else{
                        if (key.length() != 0) {
                            //Создаем новый файл-каталог с путем из переменной mainWay
                            File newFile = new File(mainWay);
                            //Для всех файлов, находящихся по указанному пути в newFile
                            for (File file : newFile.listFiles()){
                                //В случае, если указанный путь ведет к самому граф. файлу
                                if (file.getName().equals(key)){
                                    //Создаем значок изображения, считанного в file
                                    ImageIcon imageIcon = new ImageIcon(file.getPath());
                                    //Определяем байтовый поток вывода для клиента
                                    OutputStream outPutStream = socket.getOutputStream();
                                    //Создаем буфер для потоков вывода и вносим туда наш поток
                                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outPutStream);
                                    //Создаем изображение, получив его из значка
                                    Image image = imageIcon.getImage();
                                    //Импортируем изображение из буфера, задав ширину, высоту и тип(с 8-разрядными компонентами цвета RGB)
                                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
                                    //П подизображение, определенное указанной ранее прямоугольной областью
                                    Graphics graphics = bufferedImage.createGraphics();
                                    //Вытягиваем из директории изображение в угол 0;0
                                    graphics.drawImage(image, 0, 0, null);
                                    graphics.dispose();
                                    ImageIO.write(bufferedImage, "jpg", bufferedOutputStream);//создается jpg изображение ....?
                                    //Закрываем выходной поток и буфер
                                    outPutStream.close();
                                    bufferedOutputStream.close();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}