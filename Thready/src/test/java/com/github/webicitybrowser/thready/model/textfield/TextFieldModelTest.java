package com.github.webicitybrowser.thready.model.textfield;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TextFieldModelTest {

	@Test
	@DisplayName("Text field retains set value")
	public void textFieldRetainsSetValue() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		Assertions.assertEquals("hello", textField.getText());
	}
	
	@Test
	@DisplayName("Text field retains cursor position")
	public void textFieldRetainsCursorPosition() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		textField.setCursorPos(3);
		Assertions.assertEquals(3, textField.getCursorPos());
	}
	
	@Test
	@DisplayName("Cursor position capped at text size")
	public void cursorPositionCappedAtTextSize() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		textField.setCursorPos(7);
		Assertions.assertEquals(5, textField.getCursorPos());
	}
	
	@Test
	@DisplayName("Can insert text at cursor before end")
	public void canInsertTextAtCursorBeforeEnd() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		textField.setCursorPos(2);
		textField.insert("y, fe");
		Assertions.assertEquals("hey, fello", textField.getText());
		Assertions.assertEquals(7, textField.getCursorPos());
	}
	
	@Test
	@DisplayName("Can replace text at cursor before end")
	public void canReplaceTextAtCursorBeforeEnd() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		textField.setCursorPos(2);
		textField.replace("yo");
		Assertions.assertEquals("heyoo", textField.getText());
		Assertions.assertEquals(4, textField.getCursorPos());
	}
	
	@Test
	@DisplayName("Can replace text at cursor past end")
	public void canReplaceTextAtCursorPastEnd() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		textField.setCursorPos(2);
		textField.replace("y Jude");
		Assertions.assertEquals("hey Jude", textField.getText());
		Assertions.assertEquals(8, textField.getCursorPos());
	}
	
	@Test
	@DisplayName("Can delete upcoming text")
	public void canDeleteUpcomingText() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		textField.setCursorPos(2);
		textField.delete(2);
		Assertions.assertEquals("heo", textField.getText());
	}
	
	@Test
	@DisplayName("Can delete previous text")
	public void canDeletePreviousText() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		textField.setCursorPos(2);
		textField.delete(-2);
		Assertions.assertEquals("llo", textField.getText());
		Assertions.assertEquals(0, textField.getCursorPos());
	}
	
	@Test
	@DisplayName("Can handle over deletion of upcoming text")
	public void canHandleOverDeletionOfUpcomingText() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		textField.setCursorPos(2);
		textField.delete(7);
		Assertions.assertEquals("he", textField.getText());
	}
	
	@Test
	@DisplayName("Can handle over deletion of previous text")
	public void canHandleOverDeletionOfPreviousText() {
		TextFieldModel textField = TextFieldModel.create();
		textField.setText("hello");
		textField.setCursorPos(2);
		textField.delete(-3);
		Assertions.assertEquals("llo", textField.getText());
		Assertions.assertEquals(0, textField.getCursorPos());
	}
	
}
