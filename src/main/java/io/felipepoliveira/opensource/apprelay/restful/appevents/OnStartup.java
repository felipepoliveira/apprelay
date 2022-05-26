package io.felipepoliveira.opensource.apprelay.restful.appevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.app.services.ApplicationService;

@Component
public class OnStartup {
	
	@Autowired
	private ApplicationService applicationService;
	
	@EventListener({ContextRefreshedEvent.class})
	private void triggerOnLoad() {
		applicationService.executeOnLoad();
	}

}
