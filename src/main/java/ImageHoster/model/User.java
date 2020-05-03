package ImageHoster.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity annotation specifies that the corresponding class is a JPA entity
@Entity
//@Table annotation provides more options to customize the mapping.
//Here the name of the table to be created in the database is explicitly mentioned as 'users'. Hence the table named 'users' will be created in the database with all the columns mapped to all the attributes in 'User' class
@Table(name = "users")
public class User {

    //@Id annotation specifies that the corresponding attribute is a primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column annotation specifies that the attribute will be mapped to the column in the database.
    //Here the column name is explicitly mentioned as 'id'
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    //The 'users' table is mapped to 'user_profile' table with One:One mapping
    //cascade = CascadeType.ALL specifies that if a record in 'user_profile' table is deleted or updated, then all the records in 'users' table associated to that particular record in 'user_profile' table will be deleted or updated  first and then the record in the 'user_profile' table will be deleted or updated
    //FetchType is EAGER
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //Below annotation indicates that the name of the column in 'users' table referring the primary key in 'user_profile' table will be 'profile_id'
    @JoinColumn(name = "profile_id")
    private UserProfile profile;


    //The 'users' table is referenced by the 'images' table
    //The table (primary key) is referenced by the 'user' field in the 'images' table
    //cascade = CascadeType.REMOVE specifies that if a record in 'users' table is deleted, then all the records in 'images' table associated to that particular record in 'users' table will be deleted first and then the record in the 'users' table will be deleted
    //FetchType is LAZY
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}

