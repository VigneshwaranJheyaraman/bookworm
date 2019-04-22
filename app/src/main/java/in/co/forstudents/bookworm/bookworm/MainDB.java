package in.co.forstudents.bookworm.bookworm;

/**
 * Created by VRAj on 20-02-2018.
 */

public class MainDB {
    private int id;
    private String bookname;
    private String imageLink;
    private String bookgenre;

    public MainDB(int id, String bookname, String imageLink, String bookgenre) {
        this.id = id;
        this.bookname = bookname;
        this.imageLink = BookDatabaseActivity.server_url+"/bookworm/"+imageLink;
        this.bookgenre = BookDatabaseActivity.server_url+"/bookworm/"+bookgenre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname= bookname;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getBookgenre() {
        return bookgenre;
    }

    public void setBookgenre(String bookgenreo) {
        this.bookgenre = bookgenre;
    }
}
