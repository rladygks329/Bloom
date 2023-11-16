create or replace TRIGGER LESSON_INDEX_INSERT_TRIGGER
BEFORE INSERT ON LESSON
FOR EACH ROW
DECLARE
    max_lesson_index number;
BEGIN 
    SELECT NVL(MAX(lesson_index), 0) INTO max_lesson_index
    FROM LESSON
    WHERE lecture_id = :new.lecture_id;
    
    :NEW.LESSON_INDEX := max_lesson_index + 1024;
END;

