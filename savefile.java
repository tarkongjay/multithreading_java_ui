public class savefile {

    private int id;
    private String name;
    private byte[] data;
    private String fileLastname;

    public savefile(int id, String name, byte[] data, String fileLastname) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.fileLastname = fileLastname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setFileExtension(String fileExtension) {
        this.fileLastname = fileExtension;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public String getFileExtension() {
        return fileLastname;
    }
}

