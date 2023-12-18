package ru.geekbrains.models;
import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "Courses")
public class Courses {

    private static final String[] titles = new String[] {"Introduction to Java", "JDK",
            "JavaJunior", "MySQL", "Git"};

    private static final Random random = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // id курса
    @Column(name = "id")
    private int id;

    // название курса
    @Column(name = "title")
    private String title;

    // продолжительность курса в месяцах
    @Column(name = "duration")
    private int duration;

    public Courses(){

    }

    public Courses (int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }
    public Courses (String title, int duration) {
        this.title = title;
        this.duration = duration;
    }
    public static Courses create(){
        return new Courses(titles[random.nextInt(titles.length)], random.nextInt(3,6));
    }

    public  void updateDuration() {
        duration = random.nextInt(3,6);
    }
    public  void updateTitle() {
        title = titles[random.nextInt(titles.length)];
    }
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
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
