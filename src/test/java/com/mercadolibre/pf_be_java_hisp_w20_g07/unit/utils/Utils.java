package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.utils;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<List<Batch>> batchStocks(){

        List<List<Batch>> batchStocks = new ArrayList<>();
        batchStocks.add(new ArrayList<>());
        batchStocks.get(0).add(new Batch(1,200,200,12.0,12.0,null,null,Utils.products().get(0), null,null,null));
        batchStocks.get(0).add(new Batch(2,300,300,11.0,12.0,null,null,Utils.products().get(1), null,null,null));

        return batchStocks;
    }


    public static List<WareHouse> wareHouses(){

        List<Batch> batches = batchStocks().get(0);

        List<Section> sections = new ArrayList<>();

        sections.add(new Section(1,10,18,10,null,
                new Category(1,"FF","Fresh Food",null),batches));


        List<WareHouse> wareHouses = new ArrayList<>();
        User usermock = new User(1,"Tomas","tomas123",new Role("REPRESENTANTE"),null,null);

        WareHouse wareHousemock = new WareHouse(1,"warehouse1","Colombia","Bogota","cll 185 #10-3",usermock,sections);

        wareHouses.add(wareHousemock);

        usermock = new User(2,"Tomas1","tomas1234",new Role("REPRESENTANTE"),null,null);
        wareHousemock = new WareHouse(2,"warehouse2","Colombia","Bogota","cll 185 #10-3",usermock,sections);

        wareHouses.add(wareHousemock);

        return wareHouses;

    }



    public static List<User> users(){
        List<User> users = new ArrayList<>();
        User usermock = new User(1,"Tomas","tomas123",new Role("REPRESENTANTE"),wareHouses().get(0),null);
        users.add(usermock);
        User usermock2 = new User(2,"Manuel","manuel123",new Role("REPRESENTANTE"),wareHouses().get(1),null);
        users.add(usermock2);
        System.out.println(usermock.getWareHouse().getId());
        return  users;
    }

    public static List<Section> sections(){

        List<Batch> batches = batchStocks().get(0);

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

    public static List<InboundOrder> inboundOrders(){
        List<InboundOrder> inboundOrders = new ArrayList<>();
        inboundOrders.add(new InboundOrder(1,null,batchStocks().get(0)));
        inboundOrders.add(new InboundOrder(2,null,batchStocks().get(0)));
        return inboundOrders;
    }
}
