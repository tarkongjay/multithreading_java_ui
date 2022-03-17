
import java.io.EOFException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class Client {

    static ArrayList<savefile> Files = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        JFrame Frame1 = new JFrame("Client");
        Frame1.setSize(700, 450);
        Frame1.setLayout(new BoxLayout(Frame1.getContentPane(), BoxLayout.Y_AXIS));
        Frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel Panel1 = new JPanel();
        Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));
        JScrollPane scrolllist = new JScrollPane(Panel1);
        scrolllist.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel text1 = new JLabel("Port 25565");
        text1.setFont(new Font("AngsanaUPC", Font.BOLD, 20));
        text1.setAlignmentX(Component.CENTER_ALIGNMENT);

        int fileId = 0;
        int n = 0;
        Frame1.add(scrolllist);
        Frame1.setVisible(true);
        Frame1.add(text1);

        Socket socket = new Socket("localhost", 25565);
        while (true) {
            try {
                DataInputStream dataInputKerry = new DataInputStream(socket.getInputStream());
                int filelength = dataInputKerry.readInt();
                if (filelength > 0) {
                    byte[] filenamebytes = new byte[filelength];
                    dataInputKerry.readFully(filenamebytes, 0, filenamebytes.length);
                    String filename = new String(filenamebytes);
                    int filecontentLength = dataInputKerry.readInt();
                    if (filecontentLength > 0) {
                        byte[] filedata = new byte[filecontentLength];
                        dataInputKerry.readFully(filedata, 0, filedata.length);

                        JPanel FileListRow = new JPanel();
                        FileListRow.setLayout(new BoxLayout(FileListRow, BoxLayout.X_AXIS));
                        JLabel showlistName = new JLabel(filename, SwingConstants.LEFT);
                        showlistName.setHorizontalAlignment(SwingConstants.LEFT);
                        showlistName.setBorder(new EmptyBorder(12, 0, 10, 0));

                        if (FileLastname(filename).equalsIgnoreCase("txt")) {
                            FileListRow.setName((String.valueOf(fileId)));
                            FileListRow.addMouseListener(clickdownload(filename, filedata, FileLastname(filename), socket));
                            FileListRow.add(showlistName);
                            Panel1.add(FileListRow);
                            Frame1.validate();
                        } else {
                            FileListRow.setName((String.valueOf(fileId)));
                            FileListRow.addMouseListener(clickdownload(filename, filedata, FileLastname(filename), socket));
                            FileListRow.add(showlistName);
                            Panel1.add(FileListRow);
                            Frame1.validate();
                        }
                        Files.add(new savefile(fileId, filename, filedata, FileLastname(filename)));

                        fileId++;

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String FileLastname(String fileName) {
        int aaaaaaaa = fileName.lastIndexOf('.');
        if (aaaaaaaa > 0) {
            return fileName.substring(aaaaaaaa + 1);
        } else {
            return "No extension.";
        }
    }

    public static MouseListener clickdownload(String name, byte[] data, String extension, Socket S) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    JPanel panelname = (JPanel) e.getSource();
                    int fileId = Integer.parseInt(panelname.getName());
                    File downloadfile = new File(name);

                    PrintWriter out = new PrintWriter(S.getOutputStream(), true);

                    for (savefile savefiles : Files) {
                        if (savefiles.getId() == fileId) {
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(downloadfile);
                                fileOutputStream.write(data);
                                fileOutputStream.close();

                                out.println(name);

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
    }
}
