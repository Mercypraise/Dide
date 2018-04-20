package apps.amazon.com.dide.models;

import java.util.ArrayList;

public class PostModel{

    public String author, title, message;
    public ArrayList<String> comments;
    public ArrayList<String> commenters;
    public String id;

    public PostModel(String author, String title, String message, String id, ArrayList<String> comments, ArrayList<String> commenters) {
        this.author = author;
        this.title = title;
        this.message = message;
        this.comments = comments;
        this.commenters = commenters;
        this.id = id;
    }

    public PostModel(){

    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public ArrayList<String> getCommenters() {
        return commenters;
    }
}
