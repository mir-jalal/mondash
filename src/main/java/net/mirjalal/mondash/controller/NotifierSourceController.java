package net.mirjalal.mondash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import net.mirjalal.mondash.model.factory.SourceFactory;
import net.mirjalal.mondash.model.factory.SourceFactory.SourceType;
import net.mirjalal.mondash.service.NotifierSourceService;
import net.mirjalal.mondash.service.SourceService;

@Controller
@AllArgsConstructor
@RequestMapping("/api/notifier-source")
public class NotifierSourceController extends SourceController {
    private final NotifierSourceService notifierSourceService;
    private final SourceType sourceType = SourceFactory.SourceType.NOTIFIER;

	@Override
	public SourceType getSourceType() {
		return this.sourceType;
	}

	@Override
	public SourceService getSourceService() {
        return this.notifierSourceService;
    }
}
