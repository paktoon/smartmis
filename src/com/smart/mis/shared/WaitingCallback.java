package com.smart.mis.shared;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.util.SC;

public class WaitingCallback implements DSCallback {

	@Override
	public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
		SC.clearPrompt();
	}
}
