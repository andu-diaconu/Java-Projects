package BusinessLayer;

import java.io.Serializable;

public enum Type{
    EMPLOYE("Employe"), CLIENT("Client"), ADMIN("Admin");

    private String type;
    Type(String type) {
        this.type = type;
    }

    public static Type getType(String type){
        if(type.equals("Employe"))
            return Type.EMPLOYE;
        if(type.equals("Client"))
            return Type.CLIENT;
        if(type.equals("Admin"))
            return Type.ADMIN;
        return null;
    }
}
