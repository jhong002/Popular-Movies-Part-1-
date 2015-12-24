package com.captech.jhong.popularmovies.bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by jhong on 12/17/15.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);

    private BusProvider(){

    }
    public static Bus getInstance(){
        return BUS;
    }
}
