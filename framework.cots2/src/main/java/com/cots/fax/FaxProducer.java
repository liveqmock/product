package com.cots.fax;

import java.awt.Image;

public interface FaxProducer {

    public abstract Image getFaxPage(int i);
}
