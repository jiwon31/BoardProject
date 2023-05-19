import { useLocation } from "react-router-dom";
import { useMemo } from "react";

export default function useSearch() {
  const { search } = useLocation();
  const searchParams = useMemo(() => new URLSearchParams(search), [search]);

  return { searchParams };
}
