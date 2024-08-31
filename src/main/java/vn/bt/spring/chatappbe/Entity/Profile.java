package vn.bt.spring.chatappbe.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

import java.sql.Date;

@Embeddable
public class Profile {
    private String bio;
    private Date birthday;
    private Boolean gender;
    private String image;

    public Profile() {
    }

    public Profile(String bio, Date birthday, Boolean gender, String image) {
        this.bio = bio;
        this.birthday = birthday;
        this.gender = gender;
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "bio='" + bio + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", image='" + image + '\'' +
                '}';
    }
}
