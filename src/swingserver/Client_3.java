package swingserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client_3 {

    public static void main(String[] args) {
        //МОЖНО ПОЖАЛУЙСТА ЧУТЬ ПОДРОБНЕЕ ОБЪЯСНИТЬ ДЛЯ ЧЕГО НАМ lstFromServerSplit И lstButtons
        //lstButtons представляет собой хранилище кнопок, лист, который состоит из листов, то есть сборник который состоит из поддиректорий,
        //над которыми впоследуюшем происходят действия либо поддиректория удаляется при Back, либо добавляется новая поддиректория
        List<List<JButton>> lstButtons = new ArrayList<>(); //Список из групп всех элементов в окне Клиент?
        //????Список элементов по указанному пути?
        //lstFromServerSplit представляет собой хранилище УЖЕ НАЗВАНИЙ!!!!! А НЕ САМИХ КНОПОК кнопок, лист,
        //который состоит из листов, то есть сборник который состоит из поддиректорий,
        //над которыми впоследуюшем происходят действия либо поддиректория удаляется при Back, либо добавляется новая поддиректория
        List<List<String>> lstFromServerSplit = new ArrayList<>();
        //Создадим список для пути
        List<String> path=new ArrayList<>();
        //Создадим окно c названием Client
        JFrame jFrame=new JFrame("Client");
        //Зададим дизайн созданного окна
        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds(dim.width/2-300,dim.height/2-250,600,500);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        //Установим операцию закрытия
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Создадим панель-контейнер внутри окна для пути
        JPanel jpanelPath=new JPanel();
        //Создадим кнопку с текстом "Открыть директорию"
        JButton openDyrectory=new JButton("Открыть директорию");

        //НЕ ОЧЕНЬ ПОНИМАЮ ОТСЮДА
        List<JButton> jButtons=new ArrayList<>();//Создадим список кнопок
        jButtons.add(openDyrectory);//Добавим в список кнопку "Открыть директорию"
        lstButtons.add(jButtons);//добавим созданный список кнопок внутрь списка lstButtons(список элементов окна Клиент)
        List<String> stringList=new ArrayList<>();//создадим список строк
        stringList.add(openDyrectory.getText());//добавим в него текст кнопки "Открыть директорию"
        lstFromServerSplit.add(stringList);//добавим список строк внутрь еще одного списка lstFromServerSplit
        //ДОСЮДА

        //Создадим панель-контейнер внутри окна Клиент
        JPanel jPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));

        //Добавим в созданную панель кнопку
        jPanel.add(openDyrectory);
        //Пропишем размеры панели и добавим в нее 2 полосы прокрутки
        jPanel.setPreferredSize(new Dimension(600,1000));
        JScrollPane jScrollPane=new JScrollPane(jPanel);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //Зададим дизайн для полосы прокрутки
        jScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI(){
            @Override
            protected void configureScrollBarColors(){
                this.thumbColor=Color.decode("#D3D3D3");
            }
        });
        jScrollPane.setPreferredSize(dim);
        jPanel.setBackground(Color.white);
        //Добавим полосу прокрутк к окну Клиент
        jFrame.add(jScrollPane);
        //Создадим компонент для преставления объектов в виде меню
        JMenuBar jToolBar=new JMenuBar();
        jToolBar.add(Box.createHorizontalBox());
        //Добавим меню к окну Клиент
        jFrame.add(jToolBar);
        //Добавим в панель для пути в нижней части окна подпись-путь(пока пустую)
        jpanelPath.add(new JLabel(""));
        jFrame.add(jpanelPath,BorderLayout.NORTH);

        //Создадим экземпляр нашего Клиента
        Client_3 client_3 = new Client_3();
        //Добавим в экземпляр Клиента все созданные ранее элементы и сделаем окно видимым
        client_3.getAllButtons(lstButtons,lstFromServerSplit,jFrame,jPanel,path,jpanelPath,jToolBar);
        //Установим дизайн кнопки "Открыть директорию"
        client_3.buttonDesign(openDyrectory);
        openDyrectory.setHorizontalAlignment(SwingConstants.CENTER);

        //Зададим изменение цвета кнопки "Открыть директорию" при наведении мышки
        client_3.changeColor(openDyrectory);
        jFrame.setVisible(true);
        /*for (JButton jbt : lstButtons.get(lstButtons.size()-1)) {
            jPanel.add(jbt);
        }*/
        jPanel.revalidate();//проверка корректности панели
    }
    //Метод для дизайна кнопок
    public void buttonDesign(JButton button){
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200,40));
        button.setBackground(Color.white);
    }
    //Метод для смены цвета кнопки при наведении
    public void changeColor(JButton button){
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground( Color.decode("#dbeaff"));
            }
            @Override
            public void mouseExited(MouseEvent e) { button.setBackground( Color.white); }
        });
    }
    //МЕТОД ДЛЯ ...... (как правильно описать цель?)
    //ОСНОВНОЙ МЕТОД ДЛЯ ОБРАБОТКИ КНОПОК ПРИ ПЕРЕХОДАХ И ОТВЕТЕ НА НАЖАТИЯ
    public void getAllButtons(List<List<JButton>> lstButtons, List<List<String>> lstFromServerSplit,JFrame jFrame,JPanel jPanelForButon,List<String> path,JPanel jpanelPath,JMenuBar jToolBar) {
        //.
        //НЕ ОЧЕНЬ ПОНИМАЮ ОТСЮДА
        //ЕСЛИ ФЛАГ==0 - МЫ УДАЛЯЕМ ПОСЛЕДНИЙ ЭЛЕМЕНТ И ПИШЕМ ФЛАГ++
        //СЛЕДОВАТЕЛЬНО ВОПРОС, ПРИ КАКИХ ДЕЙСТВИЯХ МЫ ВХОДИМ В СЛЕДУЮЩИЙ ЦИКЛ, ГДЕ УСЛОВИЕ ФЛАГ==0?
        //
        /*int flag = 0;
        if (flag == 0)
            //?????????
            for (int i = 0; i < lstFromServerSplit.size() - 1; i++) {
                if (lstFromServerSplit.get(i).equals(lstFromServerSplit.get(lstFromServerSplit.size() - 1))) {//????
                    lstFromServerSplit.remove(lstFromServerSplit.size() - 1);//удалим последний элемент из lstFromServerSplit
                    lstButtons.remove(lstButtons.size() - 1);//удалим последний элемент из lstButtons
                    ++flag;
                }
            }*/
       // if (flag == 0) {
            //Здесь вы берем последнюю поддиректорию которая у нас есть, и для нее
            for (JButton jbt : lstButtons.get(lstButtons.size() - 1)) {
                //?????????
                //Правильно ли ниже?
                //Возвращаемся к предыдущей "странице", если последняя кнопка в lstButtons (нажатая?) - кнопка "Back"
                if (jbt.getText().equals("Back")) {
                    //Обновим меню и добавим в него кнопку "Back"
                    jToolBar.removeAll();
                    jToolBar.revalidate();
                    jToolBar.repaint();
                    jToolBar.add(jbt);
                    //Изменение цвета кнопки при нваедении мышки
                    changeColor(jbt);
                    //Задание дизайна кнопки "Back"
                    buttonDesign(jbt);

                    //Зададим обработку НА НАЖАТИЕ для кнопки "Back", ЭТО ПРОИСХОДИТ ТОЛЬКО КОГДА ПОЛЬЗОВАТЕЛЬ НАЖАЛ
                    jbt.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (lstButtons.size() != 1 && lstButtons.size() != 0) {
                                path.remove(path.size()-1);
                                //Создаем сокет-клиента
                                Socket socket = null;
                                try {
                                    //Пробуем инициализировать клиента и создать соответствующий выходной поток
                                    socket = new Socket("localhost", 8080);
                                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                                    //Записываем в строку текст из кнопки и помещаем его в выходной поток
                                    String o = jbt.getText();
                                    dataOutputStream.writeUTF(o);
                                    dataOutputStream.flush();
                                }
                                //Обработка ошибки ввода-вывода
                                catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                //КАК ГРАМОТНО ОПИСАТЬ ЗАЧЕМ ЭТО ПРОИСХОДИТ?
                                //по сути мы удаляем все что было на панеле, чтобы вывести новые кнопки, так как мы идем назад,
                                //удаляем старые КНОПКИ И ИХ НАЗВАНИЯ то есть отходим на директорию назад
                                jPanelForButon.removeAll();
                                lstFromServerSplit.remove(lstFromServerSplit.size() - 1);
                                lstButtons.remove(lstButtons.size() - 1);

                                //ЕСЛИ НАЖАТАЯ КНОПКА НЕ "BACK", ТО ДОБАВЛЯЕМ ЕЕ В ПАНЕЛЬ?-ДА

                                for (JButton jbt : lstButtons.get(lstButtons.size() - 1)) {
                                    if(!jbt.getText().equals("Back")){
                                        jPanelForButon.add(jbt);
                                    }
                                }
                                //ДОСЮДА


                                System.out.println(path.toString()+"inBack");
                                //Создаем компонент для указания в нем пути
                                JLabel jLabel=new JLabel();
                                //Создаем строку для пути из списка path
                                String stringPath="";
                                for(String str:path){
                                    stringPath=stringPath+str;
                                }
                                //Преобразовываем полученные из списка данные
                                stringPath = stringPath.replace("[", "");
                                stringPath = stringPath.replace("]", "");
                                stringPath = stringPath.replace(",", "");
                                //Зададим компоненту для пути новое значение stringPath
                                jLabel.setText(stringPath);
                                //Обновим нижнюю панель для пути и добавим в нее новый созданный компонент
                                jpanelPath.removeAll();
                                jpanelPath.revalidate();
                                jpanelPath.add(jLabel);
                                //Обновим основную панель и наше окно Клиент
                                jPanelForButon.revalidate();
                                jPanelForButon.repaint();
                                //jFrame.add(jPanel2,BorderLayout.NORTH);
                                jFrame.revalidate();
                                jFrame.repaint();
                            }
                        }
                    });
                }
                //Если последняя кнопка в списке (нажатая?) НЕНАЖАТАЯ МЫ ПРОСТО РАСКИДЫВАЕМ ОБРАБОТЧИКИ ДЛЯ НАШЕЙ ПОЛУЧЕННОЙ НОВОЙ ПОДДИРЕКТОРИИИ
                // не "Back"
                else {

                    jbt.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                //Создаем сокет-клиента и выходной поток для этого сокета
                                Socket socket = new Socket("localhost", 8080);
                                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                                //Получаем тект из нажатой? ДА кнопки
                                String o = jbt.getText();
                                //В выходной поток записываем полученный текст
                                dataOutputStream.writeUTF(o);
                                //Сбрасываем поток и записываем все буферизованные выходные байты
                                dataOutputStream.flush();
                                //Если мы нажали не на графический файл, то
                                if (!jbt.getText().contains("png") && !jbt.getText().contains("jpg") && !jbt.getText().contains("bmp") && !jbt.getText().contains("pds") && !jbt.getText().contains("gif") && !jbt.getText().contains("tif"))
                                {
                                    while (true) {
                                        System.out.println();
                                        //Если у нас есть сокет-клиент
                                        if (socket != null)
                                        {
                                            //Указываем, как выглядит путь внизу окна при открытии файлов внутри директории,
                                            //добавляя к списку path данные
                                            //Если мы нажали на что-то внутри директории, то пример пути: "directory>имя"
                                            if(!jbt.getText().equals("Открыть директорию")){
                                                path.add(">"+jbt.getText());
                                            }
                                            //Иначе наш путь "directory"
                                            else{
                                                path.add("directory");
                                            }
                                            System.out.println(path.toString());
                                            //Создаем компонент-строчку для пути
                                            JLabel jLabel=new JLabel();
                                            String stringPath="";
                                            //Обновляем значение пути из списка
                                            for(String str:path){
                                                stringPath=stringPath+str;
                                            }
                                            //Преобразуем данные, взятые из списка для строки stringPath
                                            stringPath = stringPath.replace("[", "");
                                            stringPath = stringPath.replace("]", "");
                                            stringPath = stringPath.replace(",", "");
                                            //Устанавливаем в строчку для пути новое значение, добавляем ее в панель для пути и обновляем
                                            jLabel.setText(stringPath);
                                            jpanelPath.removeAll();
                                            jpanelPath.revalidate();
                                            jpanelPath.add(jLabel);
                                            //Создаем экземпляр входного потока для клиента и берем оттуда строку в utf8 кодировке
                                            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                                            String utf = dataInputStream.readUTF();
                                            //Если строка не пустая
                                            if (utf.length() != 0) {
                                                //НЕ ОЧЕНЬ ПОНЯЛА ЧТО ЗДЕСЬ КОНКРЕТНО ОТСЮДА
                                                //ЕСЛИ ПРИХОДИТ <Empty> ОТ СЕРВЕРА ЗНАЧИТ МЫ ПРОСТО ОТКРЫВАЕМ ПАПКУ ПУСТУЮ КАК НИ В ЧЕМ НЕ БЫВАЛО,
                                                //ПРОПИСЫВАЕМ ВСЕ КАК ВСЕГДА ВНИЗУ ПУТЬ И BACK И ТИПО ПУСТО
                                                if(utf.equals("<Empty>")){
                                                    //здесь берем и создаем лист кнопок и лист названий этих кнопок ЧИСТЫЕ
                                                    //добавляем в основные листы в самый конец lstFromServerSplit и lstFromServerSplit
                                                    //и в этих чистых листах создаем кнопку back
                                                    List<String> innerName = new ArrayList<>();
                                                    List<JButton> innerButton = new ArrayList<>();
                                                    lstFromServerSplit.add(innerName);
                                                    lstButtons.add(innerButton);
                                                    lstFromServerSplit.get(lstFromServerSplit.size() - 1).add("Back");
                                                    lstButtons.get(lstButtons.size() - 1).add(new JButton("Back"));
                                                    //ДОСЮДА

                                                    //Обновляем панель для кнопок
                                                    jPanelForButon.removeAll();
                                                    jPanelForButon.revalidate();
                                                    jPanelForButon.repaint();
                                                    //Обновляем окно Клиент
                                                    jFrame.revalidate();
                                                    jFrame.repaint();
                                                    for (JButton jbt : lstButtons.get(lstButtons.size() - 1)) {
                                                        jPanelForButon.add(jbt);
                                                    }
                                                    //jPanelForButon.add(jLabel);
                                                    jPanelForButon.revalidate();
                                                    //Создаем новый экземпляр класса Клиент с новыми обновленными элементами
                                                    Client_3 client_3 = new Client_3();
                                                    client_3.getAllButtons(lstButtons, lstFromServerSplit, jFrame, jPanelForButon,path,jpanelPath,jToolBar);
                                                    socket.close();
                                                    break;
                                                }

                                                //НЕ ОЧЕНЬ ПОНИМАЮ ОТСЮДА
                                                //ЕСЛИ ПРИХОДИТ НЕ <Empty> ОТ СЕРВЕРА ЗНАЧИТ
                                                //здесь берем и создаем лист кнопок и лист названий этих кнопок ЧИСТЫЕ
                                                //добавляем в основные листы в самый конец lstFromServerSplit и lstFromServerSplit
                                                List<String> innerName = new ArrayList<>();
                                                List<JButton> innerButton = new ArrayList<>();
                                                lstFromServerSplit.add(innerName);
                                                lstButtons.add(innerButton);
                                                //Преобразуем строку и добавляем ее к файлам внутри каталога???? ДОБАВЛЯЕМ НАЗВАНИЯ ЭТИХ ФАЙЛОВ,
                                                //СОЗДАВАЯ НОВЫЙ СПИСОК НАЗВАНИЙ ПО СУТИ
                                                for (String s : utf.split(",")) {
                                                    s = s.replace("[", "");
                                                    s = s.replace("]", "");
                                                    lstFromServerSplit.get(lstFromServerSplit.size() - 1).add(s.trim());
                                                }
                                                //ДОСЮДА

                                                //Задаем отображение элементов внутри директории
                                                for (String key : lstFromServerSplit.get(lstFromServerSplit.size() - 1)) {
                                                    //Создаем компонент-кнопку по ключу
                                                    JButton newJB = new JButton(key);
                                                    //Если файл не графический, задаем ему иконку папки
                                                    if (!key.contains(".png") && !key.contains(".jpg") && !key.contains(".bmp") && !key.contains(".psd") && !key.contains(".gif") && !key.contains(".tif")) {
                                                        ImageIcon image=new ImageIcon("folder.png");
                                                        newJB.setIcon(image);
                                                    }
                                                    //Если файл графический, задаем ему иконку файла
                                                    else{
                                                        ImageIcon image=new ImageIcon("file.png");
                                                        newJB.setIcon(image);
                                                    }
                                                    //Изменение цвета иконок (папок и файлов) в директории при наведении мышки
                                                    changeColor(newJB);
                                                    //Дизайн иконок
                                                    buttonDesign(newJB);
                                                    //добавление папки/файла в список(директорию) lstButtons????
                                                    lstButtons.get(lstButtons.size() - 1).add(newJB);
                                                }

                                                //.
                                                //НЕ ОЧЕНЬ ПОНИМАЮ СМЫСЛ ОТСЮДА-ТЫ ВСЕ ПРАВИЛЬНО РАСПИСВЛВ ТО, ПРОСТОВ ДИРЕКТОРИЯХ ТО НЕТ ПАПКИ ИЛИ КНОПКИ BACK
                                                //ПОЭТОМУ КАЖДЫЙ РАЗ МЫ ЕЕ СОЗДАЕМ ПРИЧЕМ В КОНЦЕ-ПОСМОТРИ В КОНСОЛЬ ПОСЛЕ ТОГО КАК ЗАПУСТИШЬ И ПОТЫКАЕШЬСЯ
                                                lstFromServerSplit.get(lstFromServerSplit.size() - 1).add("Back");//добавим текст "Back" последнему элементу списка lstFromServerSplit
                                                lstButtons.get(lstButtons.size() - 1).add(new JButton("Back"));//добавим кнопку "Back" последнему элементу списка lstButtons
                                                //КОРОЧЕ ЭТО РЕДИРЕКТ ВРОДЕ, ТАМ МОЖЕТ ПРОИСХОДТЬ ДУБЛЯЖ ВРОДЕ КНОПОК И НАЗВАНИЙ А ЭТА ШТУКА ВНИЗУ ИЗБАВЛЯЕТ ОТ ЭТОГО
                                               /* for (int i = 0; i < lstFromServerSplit.size() - 1; i++) {

                                                    if (lstFromServerSplit.get(i).equals(lstFromServerSplit.get(lstFromServerSplit.size() - 1))) {//????
                                                        lstFromServerSplit.remove(lstFromServerSplit.size() - 1);//удалим последний элемент списка lstFromServerSplit
                                                        lstButtons.remove(lstButtons.size() - 1);//удалим последний элемент списка lstButtons
                                                    }
                                                }*/
                                                //ДОСЮДА
                                                //.

                                                //Обновляем панель и окно Клиент
                                                jPanelForButon.removeAll();
                                                jPanelForButon.revalidate();
                                                jPanelForButon.repaint();
                                                jFrame.revalidate();
                                                jFrame.repaint();

                                                //.
                                                //НЕ ОЧЕНЬ ПОНИМАЮ СМЫСЛ ОТСЮДА (то есть для чего мы это делаем)
                                                //Если (нажатый?) элемент - не кнопка "Back", добавляем его в панель
                                                //НУ ТАК СПТСОК ТО У НАС ТЕПЕРЬ ЕДИНЫЙ КНОПОК И НАЗВАНИЙ В КОНУЕ ЭТОГО СПИСКА БЭК,
                                                // А БЭК ЭТО НЕ ПАПКИ И НЕ ФАЙЛ ПОЭТОМУ НА РЯДОВУЮ ПАНЕЛЬ ЕЕ НЕ ДОБАВЛЯЕМ
                                                for (JButton jbt : lstButtons.get(lstButtons.size() - 1)) {
                                                    if(!jbt.getText().equals("Back")){//если текст кнопки jbt не соответствует "Back", то добавляем ее в панель
                                                        jPanelForButon.add(jbt);
                                                    }
                                                }
                                                System.out.println(lstFromServerSplit);
                                                jPanelForButon.revalidate();
                                                //Создаем экзмепляр Клиента, добавляем созданные элементы и закрываем сокет-клиента

                                                Client_3 client_3 = new Client_3();
                                                client_3.getAllButtons(lstButtons, lstFromServerSplit, jFrame, jPanelForButon,path,jpanelPath,jToolBar);
                                                socket.close();
                                                break;
                                                //ДОСЮДА
                                                //.
                                            }
                                        }
                                    }
                                } else {
                                    //ЕСЛИ ФАЙЛ ГРАФИЧЕСКИЙ ТО У НЕГО СВОЙ ОБРАБОТЧИК
                                    while (true) {
                                        System.out.println();
                                        //Если у нас открыт клиент
                                        if (socket != null) {
                                            try {
                                                //Создаем новый компонент для подписи имени открываемого граф. файла и передаем в него имя
                                                JLabel jLabelString=new JLabel();
                                                jLabelString.setText(jbt.getText());
                                                //jLabelString.setLayout(new BorderLayout.NORTH);
                                                //Создаем входной поток и записываем туда данные входного потока клиента
                                                InputStream inputStream = socket.getInputStream();
                                                //Создаем буфер и получаем в него граф. файл
                                                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                                                BufferedImage bufferedImage = ImageIO.read(bufferedInputStream);
                                                bufferedInputStream.close();
                                                //Создаем окно, в к-м откроется граф. файл для сохранения
                                                JFrame jFrame = new JFrame();
                                                Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
                                                //Создаем иконку граф. файла передаем в нее файл из буфера
                                                //На гифках здесь вылазит ошибка, что изображения никакого нет
                                                ImageIcon imageIcon=new ImageIcon(bufferedImage);
                                                int w=imageIcon.getIconWidth();
                                                int h=imageIcon.getIconWidth();
                                                //Задаем размеры созданного окна
                                                if(w>dim.width||h>dim.height)
                                                {
                                                    jFrame.setSize(dim.width,dim.height);
                                                }
                                                else
                                                {
                                                    jFrame.setBounds(dim.width/2-w/2,dim.height/2-h/2,w+100,h+100);
                                                }
                                                //jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
                                                //jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                                //Создаем буферизованную панель, где будут кнопки "Отмена","Сохранить" и граф.файл
                                                JPanel jPanel = new JPanel();
                                                //Добавляем внизу созданного окна наш компонент для подписи имени
                                                jFrame.add(jLabelString,BorderLayout.SOUTH);
                                                //Устанавливаем размер панели и создаем в ней полосы прокрутки
                                                jPanel.setPreferredSize(new Dimension(w+100,h+100));
                                                JScrollPane jScrollPane=new JScrollPane(jPanel);
                                                jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                                                //Зададим дизайн для вертикальной полосы прокрутки
                                                jScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI(){
                                                                                             @Override
                                                                                             protected void configureScrollBarColors(){
                                                                                                 this.thumbColor=Color.decode("#D3D3D3");
                                                                                             }
                                                                                         }
                                                );
                                                //Зададим дизайн для горизонтальной полосы прокрутки
                                                jScrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI(){
                                                    @Override
                                                    protected void configureScrollBarColors(){
                                                        this.thumbColor=Color.decode("#D3D3D3");
                                                    }
                                                });
                                                //Устанавливаем дизайн панели и добавляем ее в окно
                                                jPanel.setBackground(Color.decode("#9FCABE"));
                                                jFrame.add(jScrollPane);
                                                jFrame.setVisible(true);
                                                //Создаем компонент для иконки нашего файла
                                                JLabel jLabel = new JLabel(new ImageIcon(bufferedImage));
                                                //Создаем и добавляем в панель кнопку "Отмена"
                                                JButton jButton = new JButton("Cancel");
                                                jPanel.add(jButton);
                                                //Функция кнопки "Отмена"
                                                jButton.addActionListener(new ActionListener() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent e) {
                                                        jFrame.dispose();
                                                    }
                                                });
                                                //Дизайн кнопки "Отмена"
                                                buttonDesign(jButton);
                                                //Изменение цвета кнопки "Отмена" при наведении
                                                changeColor(jButton);
                                                //Создание кнопки "Сохранить" и ее дизайн
                                                JButton jButton2 = new JButton("Save");
                                                buttonDesign(jButton2);
                                                //Добавление кнопки "Сохранить" к панели
                                                jPanel.add(jButton2);
                                                //Сохранение по нажатию на кнопку "сохранить"
                                                jButton2.addActionListener(new ActionListener() {
                                                    @Override

                                                    public void actionPerformed(ActionEvent e) {
                                                        //Указываем расширение, в котором можно сохранить файл
                                                        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG","jpg");
                                                        //Выводим окно для выбора места сохранения файла
                                                        JFileChooser fc = new JFileChooser();
                                                        fc.setFileFilter(filter);
                                                        //В выбранное место пытаемся сохранить файл
                                                        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
                                                            try{
                                                                //Создаем строку-полный путь для граф.файла
                                                                String str=fc.getSelectedFile().getAbsolutePath()+".jpg";
                                                                //Создаем файл по указанному в str пути
                                                                File file=new File(str);
                                                                //Записываем в него картинку в указанном формате из буфера
                                                                ImageIO.write(bufferedImage, "jpg", file);
                                                            }
                                                            //Обработка ошибки сохранения файла
                                                            catch (IOException ex ) {
                                                                System.out.println("Всё погибло!");
                                                            }
                                                        }
                                                    }
                                                });
                                                //Изменение цвета кнопки "Сохранить" при наведении
                                                changeColor(jButton2);
                                                //Добавляем к панели иконку для граф. файла
                                                jPanel.add(jLabel);
                                            }
                                            //При ошибке выдаем отчёт о действующих кадрах стека на момент ошибки
                                            catch (Exception ez) {
                                                ez.printStackTrace();
                                            }
                                            socket.close();
                                            break;
                                        }
                                    }
                                }
                            }
                            //При ошибке ввода-вывода выдаем отчёт о действующих кадрах стека на момент ошибки
                            catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    });
                }
            }
       // }
    }
}