package com.jpa.overwrite.config;

import javax.validation.GroupSequence;

@GroupSequence({ TimeNullCheck.class,TableGrapCheck.class,TitleNullCheck.class})
public interface Table1CheckSequence {
}
