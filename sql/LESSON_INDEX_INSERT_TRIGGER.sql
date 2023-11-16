create or replace TRIGGER LESSON_INDEX_INSERT_TRIGGER
BEFORE INSERT ON LESSON
FOR EACH ROW
DECLARE
    max_lesson_index number;
BEGIN 
    IF :NEW.LESSON_INDEX IS NULL THEN
        SELECT NVL(MAX(lesson_index), 0) + 1024 INTO max_lesson_index
        FROM LESSON
        WHERE lecture_id = :new.lecture_id;
        -- Set the new LESSON_INDEX value
        :NEW.LESSON_INDEX := max_lesson_index;
    END IF;
END;
