import { useLocation, useNavigate } from "react-router-dom";
import { useMemo } from "react";

export default function useSearch() {
  const navigate = useNavigate();
  const { search } = useLocation();
  const searchParams = useMemo(() => new URLSearchParams(search), [search]);

  const setSearchParams = (searchOption: string, searchText: string) => {
    cleanSearchParams(searchOption);
    searchParams.set(searchOption, searchText);
    navigate({
      pathname: "/",
      search: searchParams.toString(),
    });
  };

  // TODO: searchOption이 page가 아니라면 page를 지운다.
  function cleanSearchParams(searchOption: string) {
    if (searchOption === "title") {
      searchParams.delete("content");
      searchParams.delete("page");
    } else if (searchOption === "content") {
      searchParams.delete("title");
      searchParams.delete("page");
    }
  }

  return { searchParams, setSearchParams };
}
