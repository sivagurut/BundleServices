package com.directv.broadbandBundles.ui.masker;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/12/11
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Interface which all Maskers must implement.
 */
public interface Masker<E>
{

    public E mask(E data);

}
