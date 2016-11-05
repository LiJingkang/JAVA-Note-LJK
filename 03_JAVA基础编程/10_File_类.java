* File类的总结：

    1.文件和文件夹的创建

    2.文件的读取

    3.文件的写入

    4.文件的复制（字符流、字节流、处理流）

    5.以图片地址下载图片
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————    
* 文件和文件夹
* 相关函数
     (boolean) mkdir() 创建此抽象路径名指定的目录
     (boolean) mkdirs() 创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
     (boolean) delete() 删除此抽象路径名表示的文件或目录
     (boolean) createNewFile() 当不存在此路径名指定名称的文件时，创建一个新的空文件。
     
* 创建文件

        public static void NewFile(String pathString) {
            File file = new File(pathString);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("文件创建成功");
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } else {
                System.out.println("文件已存在");
            }
        }


* 创建文件夹

        public static void NewFileBox(String pathString) {
            File file2 = new File(pathString);
            if (!file2.exists()) {
                if (file2.mkdirs()) {
                    System.out.println("文件夹成功");
                }
            } else {
                System.out.println("文件夹存在");
                file2.delete();//销毁文件
            }
        }


* 应用：

        public static void main(String[] args) {
            NewFile("test/file.txt");
            NewFileBox("test/a/a/a/a");
        }


* Writer写入文件
* 用FileWriter写入文件

        public  static void ForFileWriter(String string,String fileName) {
            File file = new File(fileName);
            try {
                FileWriter fWriter = new FileWriter(file);
                fWriter.write(string);
                fWriter.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }


* 用BufferedWriter写入文件

        public static void ForBufferedWriter(String string,String desFile) {
            BufferedWriter bWriter = null;
            try {
                bWriter = new BufferedWriter(new FileWriter(new File(desFile)));
                bWriter.write(string.toString());
                bWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


* 应用：

        public static void main(String[] args) {
            ForFileWriter("用FileWriter写入文件", "test/writer1.txt");
            ForBufferedWriter("用BufferedWriter写入文件", "test/writer2.txt");
        }


* Reader读取文件
* 用FileReader读取文件

        public static void testReadByReader(String fileName){
            File file = new File(fileName);
            FileReader fis = null;
            try {
                fis =  new FileReader(file);
                char[] arr = new char[1024 * 1000 * 6];
                int len = fis.read(arr);
                String data = new String(arr, 0, len);
                fis.close();
                System.out.println(fileName+"中按FileReader读取的文件内容是：\n"+data);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


* 用FileInputStream读取文件

        public static void testReadByInputStream(String fileName){
            File file = new File(fileName);
            FileInputStream fis = null;
            try {
                fis =  new FileInputStream(file);
                byte[] arr = new byte[1024 * 1000 * 6];
                int len = fis.read(arr);
                String data = new String(arr, 0, len);
                fis.close();
                System.out.println(fileName+"中按FileInputStream读取的文件内容是：\n"+data);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


* 用BufferedReader读取文件

        public static void testReadByBufferedReader(String fileName) {
            BufferedReader bReader = null;
            String line = null;
            StringBuffer buffer = new StringBuffer();
            try {
                bReader =new BufferedReader(new FileReader(new File(fileName)));
                while ((line = bReader.readLine())!=null) {
                    buffer.append(line).append("\n");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            System.out.println(fileName+"中按BufferedReader读取的文件内容是：\n"+buffer.toString());
        }


* 应用：

        public static void main(String[] args) {
            testReadByInputStream("res/我.txt");
            testReadByReader("res/我.txt");
            testReadByBufferedReader("res/我.txt");
        }


* 文件的复制操作
* 字符流复制

        public static void FileCopy1(String readfile,String writeFile) {
            try {
                FileReader input = new FileReader(readfile);
                FileWriter output = new FileWriter(writeFile);
                int read = input.read();        
                while ( read != -1 ) {
                    output.write(read); 
                    read = input.read();
                }           
                input.close();
                output.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

* 字节流复制

        public static void FileCopy2(String readfile,String writeFile) {
            try {
                FileInputStream input = new FileInputStream(readfile);
                FileOutputStream output = new FileOutputStream(writeFile);
                int read = input.read();        
                while ( read != -1 ) {
                    output.write(read); 
                    read = input.read();
                }           
                input.close();
                output.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }


* 处理流复制

        public static void FileCopy3(String readfile,String writeFile) {
            BufferedReader bReader = null;
            BufferedWriter bWriter = null;
            String line = null; 
            try {
                bReader = new BufferedReader(new FileReader(new File(readfile)));
                bWriter = new BufferedWriter(new FileWriter(new File(writeFile)));
                while ((line = bReader.readLine())!=null) {
                    bWriter.write(line);
                    bWriter.newLine();
                }
                bWriter.close();
                bReader.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }


* 应用：

        public static void main(String[] args) {
            FileCopy1("res/我.txt", "test/1.txt");
            FileCopy2("res/我.txt", "test/2.txt");
            FileCopy3("res/我.txt", "test/3.txt");
            FileCopy2("res/me.jpg", "test/33.jpg");
        }

