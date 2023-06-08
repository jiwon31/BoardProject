import useSearch from "hooks/useSearch";
import { useState, useEffect } from "react";

const BOARD_SORT_OPTIONS = ["createdAt", "likeCnt"];

export default function BoardSort() {
  const [sortOption, setSortOption] = useState<string>("createdAt");
  const { searchParams, setSearchParams } = useSearch();

  const handleOptionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSearchParams("sort", e.target.value);
    setSortOption(e.target.value);
  };

  useEffect(() => {
    const sort = searchParams.get("sort");
    if (sort === null || !BOARD_SORT_OPTIONS.includes(sort)) {
      searchParams.delete("sort");
      setSortOption("createdAt");
    } else {
      setSortOption(sort);
    }
  }, [searchParams]);

  return (
    <div className="border border-gray-300 rounded-sm">
      <select
        className="py-2 px-4 outline-none"
        value={sortOption}
        onChange={handleOptionChange}
      >
        <option value="createdAt">최신순</option>
        <option value="likeCnt">좋아요순</option>
      </select>
    </div>
  );
}
