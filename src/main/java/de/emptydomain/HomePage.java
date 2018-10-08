package de.emptydomain;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
		add(new Link<Void>("bla") {

			@Override
			public void onClick() {
				setResponsePage(MainMenu.class);
			}
			
		});

		// TODO Add your page's components here

	}
}
