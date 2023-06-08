import { useLocation, useNavigate } from "react-router-dom";
import { useMemo } from "react";

export default function useSearch() {
  const navigate = useNavigate();
  const { search } = useLocation();
  const searchParams = useMemo(() => new URLSearchParams(search), [search]);

  const setSearchParams = (option: string, value: string) => {
    cleanSearchParams(option);
    searchParams.set(option, value);
    navigate({
      pathname: "/",
      search: searchParams.toString(),
    });
  };

  function cleanSearchParams(option: string) {
    if (option !== "page" && searchParams.has("page")) {
      searchParams.delete("page");
    }
    switch (option) {
      case "title":
        searchParams.delete("content");
        break;
      case "content":
        searchParams.delete("title");
    }
  }

  return { searchParams, setSearchParams };
}
