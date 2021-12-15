<<<<<<< HEAD:app/src/main/java/com/gttime/android/request/component/Announcement.java
package com.example.registration.component;
=======
package com.gttime.android.component;
>>>>>>> 251b16ca2afc971eadf5b560844329f909fed034:app/src/main/java/com/gttime/android/component/Announcement.java

public class Announcement {
    private String content;
    private String name;
    private String date;

    public Announcement(String content, String name, String date) {
        this.content = content;
        this.name = name;
        this.date = date;
    }

    public void setContent(String notice) {
        this.content = notice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
