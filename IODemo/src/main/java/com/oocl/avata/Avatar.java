package com.oocl.avata;

/**
 * Created by CHENCO7 on 7/11/2017.
 */
public class Avatar extends Intelligor {
    Intelligor intelligor;

    public Avatar(Intelligor intelligor) {
        this.intelligor = intelligor;
    }

    public void run() {
        System.out.println("avatar run ...");
        intelligor.run();
    }
}