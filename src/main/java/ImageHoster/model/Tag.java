package ImageHoster.model;

import javax.persistence.*;
import java.util.List;

//@Entity annotation specifies that the corresponding class is a JPA entity
@Entity
//@Table annotation provides more options to customize the mapping.
//Here the name of the table to be created in the database is explicitly mentioned as 'Tags'. Hence the table named 'Tags' will be created in the database with all the columns mapped to all the attributes in 'Tag' class
@Table(name = "Tags")
public class Tag {

    //@Id annotation specifies that the corresponding attribute is a primary key
    @Id
    //@Column annotation specifies that the attribute will be mapped to the column in the database.
    //Here the column name is explicitly mentioned as 'id'
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    // Write the annotation for many to many between images and tags where they are mapped by tags field in the images table
    //The 'tags' table is mapped to 'images' table with Many:Many mapping
    //One image can have multiple categories/tags and there can be multiple images under one category/tag
    //FetchType is LAZY
    //Note that no column will be generated for this attribute in the database instead a new table will be created
    //Since the mapping is Many to Many, a new table will be generated containing the two columns both referencing to the primary key of both the tables ('images', 'tags')
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private List<Image> images;

    public Tag() {
    }

    public Tag(String tagName) {
        this.name = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
