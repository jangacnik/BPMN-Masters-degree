package com.mag.bpm.services.tasks;

import com.mag.bpm.models.FormListItem;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.Variables.SerializationDataFormats;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Component;

@Component("stCheckCreditorCredibility")
@RequiredArgsConstructor
public class CheckCreditorCredibility implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    ArrayList<FormListItem> formItems = new ArrayList<>();
    // TODO REMOVE WHEN IMPLEMENTATION FINISHED
    formItems.add(new FormListItem("test", "unchecked"));
    formItems.add(new FormListItem("test2", "unchecked"));

    ObjectValue formListTyped =
        Variables.objectValue(formItems)
            .serializationDataFormat(SerializationDataFormats.JSON)
            .create();

    delegateExecution.setVariableLocal("items", formListTyped);
  }
}
