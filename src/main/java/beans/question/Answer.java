package beans.question;

public class Answer {

    private int id;
    private String link;
    private String image;
    private String text;
    private Question FK_Question;
    private int num;

    public Answer(int id, String link, String image, String text, int num) {
        this.id = id;
        this.link = link;
        this.image = image;
        this.text = text;
        this.num = num;
    }

    public Answer() {
        this.link = null;
        this.image = null;
        this.text = null;
        this.FK_Question = null;
        this.num = 0;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getFK_Question() {
        return FK_Question;
    }

    public void setFK_Question(Question FK_Question) {
        this.FK_Question = FK_Question;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
