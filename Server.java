
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.filechooser.FileSystemView;
import java.io.EOFException;
import java.util.*;
import java.text.*;

public class Server {

    public static void main(String[] args) throws IOException {
        int nub = 0;

        Date dNow = new Date();
        SimpleDateFormat ft
                = new SimpleDateFormat("[ hh:mm:ss a ] ");

        File folder = new File("C:\\os");
        File[] listfilekrub = folder.listFiles();

        final JList Filew = new JList(new File("C:\\os").listFiles());

        JFrame jFrame = new JFrame("Server port 25565");
        jFrame.setSize(700, 450);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane Filesss = new JScrollPane(Filew);
        Filesss.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Filew.setCellRenderer(new MyCellRenderer());
        Filew.setVisibleRowCount(0);

        JLabel title = new JLabel("เซิฟเวอร์");
        title.setFont(new Font("AngsanaUPC", Font.BOLD, 25));
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea t = new JTextArea();
        JScrollPane ee = new JScrollPane(t);

        jFrame.add(title);
        jFrame.add(Filesss);
        jFrame.add(ee);
        jFrame.setVisible(true);

        ServerSocket server = null;
        try {
            server = new ServerSocket(25565);
            while (true) {
                Socket client = server.accept();
                nub++;
                t.append(ft.format(dNow) + " New client connected" + " Client " + nub + " IP : "
                        + client.getInetAddress().getHostAddress() + "\n");

                ClientTelephone clientSock
                        = new ClientTelephone(client);
                new Thread(clientSock).start();

                for (int iloveyou = 0; iloveyou < listfilekrub.length; ++iloveyou) {
                    try {
                        FileInputStream fileInputKerry = new FileInputStream(listfilekrub[iloveyou].getAbsolutePath());
                        DataOutputStream dataOutputKerry = new DataOutputStream(client.getOutputStream());
                        String fileName = listfilekrub[iloveyou].getName();
                        byte[] fileNametoBytes = fileName.getBytes();
                        byte[] filesizeBytes = new byte[(int) listfilekrub[iloveyou].length()];

                        fileInputKerry.read(filesizeBytes);

                        dataOutputKerry.writeInt(fileNametoBytes.length);
                        dataOutputKerry.write(fileNametoBytes);
                        dataOutputKerry.writeInt(filesizeBytes.length);
                        dataOutputKerry.write(filesizeBytes);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MyCellRenderer extends JLabel implements ListCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof File) {
                File file = (File) value;
                setText(file.getName());
                setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                setPreferredSize(new Dimension(250, 25));
                setEnabled(list.isEnabled());
                setFont(list.getFont());
                setOpaque(true);
            }
            return this;
        }
    }

    private static class ClientTelephone implements Runnable {

        private final Socket socket;

        public ClientTelephone(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Download File : " + line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
