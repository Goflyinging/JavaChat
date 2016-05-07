package lxing.server.test;

import org.junit.Test;

import lxing.server.data.Query;

public class TEST {
	
	@Test
	public void test(){
		Query q = new Query();
		q.deleteFriend(80001, 80003);
	}

}
