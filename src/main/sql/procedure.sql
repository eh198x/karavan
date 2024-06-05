CREATE OR REPLACE FUNCTION create_article(
  title varchar(255),
  author varchar(255),
  date_published DATE
)
RETURNS text AS $$
DECLARE
  -- variable declaration (if needed)
BEGIN
  -- stored procedure body
  BEGIN
    -- Perform the INSERT
    INSERT INTO articles ("date", title, author, date_published, status)
    VALUES (CURRENT_TIMESTAMP, title, author, date_published, 'Active');
    -- If the INSERT is successful, return 'OK'
    RETURN 'OK';
  EXCEPTION
    WHEN others THEN
      -- Handle the error here (e.g., log it or raise an exception)
      RAISE NOTICE 'Error: %', SQLERRM;
      -- Rollback the transaction
      ROLLBACK;
      -- Return the error message
      RETURN 'Error: ' || SQLERRM;
  END;
END;
$$ LANGUAGE plpgsql;
