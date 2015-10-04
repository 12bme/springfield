package com.u2ware.springfield.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import com.u2ware.springfield.view.GenericView;

public class XmlView extends MappingJackson2XmlView{

	private Log logger = LogFactory.getLog(getClass());
	
    private GenericView genericView;

    public XmlView(GenericView genericView){
    	this.genericView = genericView;
    }

	@Override
	protected Object filterModel(Map<String, Object> model) {
		Object r = model.get("responseDto");
		return r;
	}

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			logger.debug(getClass());
			super.render(model, request, response);
		}catch(Exception e){
			genericView.render(e, request, response);
		}
    }

}