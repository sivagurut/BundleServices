package com.directv.bundlesIntegration.util;

import java.util.Comparator;

import com.directv.broadbandBundles.ui.model.input.Customization;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomizationComparator.
 */
public class CustomizationComparator implements Comparator<Customization>{

	/**
	 * Overridden Method
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(Customization o1, Customization o2) {
		long rank1 = o1.getRank();
        long rank2 = o2.getRank();

        if (rank1 > rank2){
            return +1;
        }else if (rank1 < rank2){
            return -1;
        }else{
            return 0;
        }
	}

}
