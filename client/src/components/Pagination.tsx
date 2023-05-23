import useSearch from "hooks/useSearch";
import { useState } from "react";

export default function Pagination({ totalPages }: { totalPages?: number }) {
  const [page, setPage] = useState<number>(0);
  const { searchParams, setSearchParams } = useSearch();

  const handleClick = (num: number) => {
    setPage(num);
    setSearchParams("page", num.toString());
  };

  return (
    <div className="flex flex-row p-3 text-lg font-semibold border border-gray-300 rounded-md">
      <button
        className="disabled:text-gray-300"
        onClick={() => handleClick(page - 1)}
        disabled={page === 0}
      >
        &lt;
      </button>
      <div className="flex-1 flex justify-center gap-x-6">
        {totalPages &&
          Array(totalPages)
            .fill(0)
            .map((_, i) => (
              <button
                key={i}
                className={`${
                  i.toString() === (searchParams.get("page") ?? "0") &&
                  "text-brand"
                }`}
                onClick={() => handleClick(i)}
              >
                {i + 1}
              </button>
            ))}
      </div>
      <button
        className="disabled:text-gray-300"
        onClick={() => handleClick(page + 1)}
        disabled={page === totalPages! - 1}
      >
        &gt;
      </button>
    </div>
  );
}
