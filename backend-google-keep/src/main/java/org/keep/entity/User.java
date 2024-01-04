package org.keep.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    @Id
    private String emailId;
    private String password;
    private String name;
}
