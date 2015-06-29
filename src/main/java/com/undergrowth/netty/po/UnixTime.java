/**
 * 
 */
package com.undergrowth.netty.po;

import java.util.Date;

/**
 * @author u1
 * @Date  2015-6-29
 */
public class UnixTime {
    private int value;
    
    public UnixTime(){
    	this((int)(System.currentTimeMillis()/1000l+2208988800L));
    }

	/**
	 * @param i
	 */
	public UnixTime(int value) {
		// TODO Auto-generated constructor stub
		this.value=value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "UnixTime:"+new Date((getValue() - 2208988800L) * 1000L).toString();
	}
    
    
}
