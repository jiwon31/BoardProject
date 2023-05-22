import { useLocation, useNavigate } from "react-router-dom";
import { useMemo } from "react";

export default function useSearch() {
  const navigate = useNavigate();
  const { search } = useLocation();
  const searchParams = useMemo(() => new URLSearchParams(search), [search]);

  const setSearchParams = (searchOption: string, searchText: string) => {
    if (searchOption === "title") {
      searchParams.delete("content");
    } else if (searchOption === "content") {
      searchParams.delete("title");
    }
    searchParams.set(searchOption, searchText);
    navigate({
      pathname: "/",
      search: searchParams.toString(),
    });
  };
  return { searchParams, setSearchParams };
}
