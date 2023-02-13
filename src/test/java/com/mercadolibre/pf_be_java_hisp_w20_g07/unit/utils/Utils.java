package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.utils;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;

import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static List<WareHouse> wareHouses(){

        List<Batch> batches = new ArrayList<>();
        batches.add(new Batch(1,200,200,12.0,12.0,null,null,null,null,null,null));

        List<Section> sections = new ArrayList<>();

        sections.add(new Section(1,10,18,10,null,
                new Category(1,"FF","Fresh Food",null),batches));


        List<WareHouse> wareHouses = new ArrayList<>();
        User usermock = new User(1,"Tomas","tomas123",new Role("REPRESENTANTE"),null,null);

        WareHouse wareHousemock = new WareHouse(1,"warehouse1","Colombia","Bogota","cll 185 #10-3",usermock,sections);

        wareHouses.add(wareHousemock);

        return wareHouses;

    }

    public static List<User> users(){
        List<User> users = new ArrayList<>();
        User usermock = new User(1,"Tomas","tomas123",new Role("REPRESENTANTE"),wareHouses().get(0),null);
        users.add(usermock);
        System.out.println(usermock.getWareHouse().getId());
        return  users;
    }

    public static List<Section> sections(){

        List<Batch> batches = new ArrayList<>();
        batches.add(new Batch(1,200,200,12.0,12.0,null,null,null,null,null,null));

        List<Section> sections = new ArrayList<>();
        sections.add(new Section(1,10,16,10,Utils.wareHouses().get(0),
                new Category(1,"FF","Fresh Food",null),batches));

        return sections;
    }

    public static List<Product> products(){
        List<Product> products = new ArrayList<>();
        products.add(new Product(1,"Fresa",2000,null,null));
        products.add(new Product(2,"Pera",3000,null,null));
        return products;
    }
}
