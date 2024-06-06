CREATE OR REPLACE FUNCTION create_article(
  title varchar(255),
  author varchar(255)
)
RETURNS json AS $$
DECLARE
  result json;
BEGIN
  -- Perform the INSERT
  BEGIN
    INSERT INTO articles ("date", title, author, status)
    VALUES (CURRENT_TIMESTAMP, title, author, 'Active');
    -- If the INSERT is successful, set the result to 'OK'
    result := json_build_object('status', 'OK');
  EXCEPTION
    WHEN others THEN
      -- Handle the error here (e.g., log it or raise an exception)
      RAISE NOTICE 'Error: %', SQLERRM;
      -- Rollback the transaction
      ROLLBACK;
      -- Set the result to an error message
      result := json_build_object('status', SQLERRM);
  END;
  RETURN result;
END;
$$ LANGUAGE plpgsql;
