DROP TABLE public.workflow;

CREATE TABLE public.workflow (
	id bigserial NOT NULL,
	exchange_id varchar NULL,
	message varchar NULL,
	status varchar NULL,
	created_at timestamptz DEFAULT now() NULL,
	updated_at timestamptz DEFAULT now() NULL
);

-- Table Triggers

create trigger workflow_updated_at before
update on public.workflow for each row execute procedure update_updated_at();


DROP TABLE public.workflow_details;

CREATE TABLE public.workflow_details (
	id bigserial NOT NULL,
	exchange_id varchar NULL,
	message varchar NULL,
	status varchar NULL
);



DROP TABLE public.articles;

CREATE TABLE articles (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(255) NOT NULL,
  date timestamp NOT NULL,
  status VARCHAR(20) NOT NULL
);


