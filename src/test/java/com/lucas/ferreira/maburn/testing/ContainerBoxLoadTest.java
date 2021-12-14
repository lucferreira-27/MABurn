package com.lucas.ferreira.maburn.testing;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.lucas.ferreira.maburn.model.ContainerBoxLoad;
import com.lucas.ferreira.maburn.model.enums.Containers;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.layout.BorderPane;

@RunWith(JfxRunner.class)
public class ContainerBoxLoadTest {
	@Test
	public void testLoadBrowserInstalleContainer() throws Exception {
		ContainerBoxLoad<BorderPane> containerBoxLoad= new ContainerBoxLoad<BorderPane>();
		BorderPane box =containerBoxLoad.load(Containers.BROWSE_INSTALLER);
		
		assertNotNull(box);
		
	}
}
