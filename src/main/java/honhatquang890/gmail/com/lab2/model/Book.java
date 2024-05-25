package honhatquang890.gmail.com.lab2.model;

public class Book {
    public int id;
    public String title;
    public String Author;
    public String Descript;
    public int Price;
    public String imgae;

    public Book(int id, String title, String author, String descript, int price, String imgae) {
        this.id = id;
        this.title = title;
        Author = author;
        Descript = descript;
        Price = price;
        this.imgae = imgae;
    }
    public Book(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDescript() {
        return Descript;
    }

    public void setDescript(String descript) {
        Descript = descript;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getImgae() {
        return imgae;
    }

    public void setImgae(String imgae) {
        this.imgae = imgae;
    }
}
