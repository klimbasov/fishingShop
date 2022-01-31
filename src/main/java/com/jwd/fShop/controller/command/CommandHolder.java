package com.jwd.fShop.controller.command;

import com.jwd.fShop.controller.command.impl.get.*;
import com.jwd.fShop.controller.command.impl.post.*;
import com.jwd.fShop.controller.constant.CommandsAlias;

import java.util.HashMap;

public class CommandHolder {
    private HashMap<String, Command> commandMapGet;
    private HashMap<String, Command> commandMapPost;

    public CommandHolder() {
        initMapGet();
        initMapPost();
    }

    private void initMapPost() {
        commandMapPost = new HashMap<>();

        commandMapPost.put(CommandsAlias.Post.ADD_PRODUCT, new AddProduct());
        commandMapPost.put(CommandsAlias.Post.SING_IN, new SignIn());
        commandMapPost.put(CommandsAlias.Post.SING_UP, new SignUp());
        commandMapPost.put(CommandsAlias.Post.SING_OUT, new SignOut());
        commandMapPost.put(CommandsAlias.Post.TO_BASKET, new ToBasket());
        commandMapPost.put(CommandsAlias.Post.ORDER, new OrderAll());
        commandMapPost.put(CommandsAlias.Post.OUT_BASKET, new OutBasket());
        commandMapPost.put(CommandsAlias.Post.CHANGE_USERNAME, new ChangeUsername());
        commandMapPost.put(CommandsAlias.Post.CHANGE_ROLE, new ChangeRole());
        commandMapPost.put(CommandsAlias.Post.EMPTY_BASKET, new EmptyBasket());
        commandMapPost.put(CommandsAlias.Post.CHANGE_LOCALE, new ChangeLocale());
        commandMapPost.put(CommandsAlias.Post.CHANGE_PRODUCT, new ChangeProduct());
    }

    private void initMapGet() {
        commandMapGet = new HashMap<>();

        commandMapGet.put(null, new ShowIndex());
        commandMapGet.put(CommandsAlias.Get.TO_INDEX, new ShowIndex());
        commandMapGet.put(CommandsAlias.Get.TO_ADMINISTRATION, new ShowAdministration());
        commandMapGet.put(CommandsAlias.Get.TO_PRODUCTS, new ShowProducts());
        commandMapGet.put(CommandsAlias.Get.TO_PRODUCT, new ShowProduct());
        commandMapGet.put(CommandsAlias.Get.TO_ORDERS, new ShowOrders());
        commandMapGet.put(CommandsAlias.Get.TO_ORDER, new ShowOrder());
        commandMapGet.put(CommandsAlias.Get.TO_BASKET, new ShowBasket());
        commandMapGet.put(CommandsAlias.Get.TO_ADD_PRODUCT, new ShowAddProduct());
        commandMapGet.put(CommandsAlias.Get.TO_PROFILE, new ShowProfile());
        commandMapGet.put(CommandsAlias.Get.TO_SING_IN, new ShowSignIn());
        commandMapGet.put(CommandsAlias.Get.TO_SING_UP, new ShowSignUp());
        commandMapGet.put(CommandsAlias.Get.TO_USERS, new ShowUsers());
        commandMapGet.put(CommandsAlias.Get.TO_ERROR, new ShowError());
        commandMapGet.put(CommandsAlias.Get.TO_CHANGE_USER, new ShowChangeUser());
        commandMapGet.put(CommandsAlias.Get.TO_USER, new ShowUser());
        commandMapGet.put(CommandsAlias.Get.TO_CHANGE_PRODUCT, new ShowChangeProduct());
    }


    public Command getGetCommandByAlias(String alias) {
        return commandMapGet.get(alias);
    }

    public Command getPostCommandByAlias(String alias) {
        return commandMapPost.get(alias);
    }
}