import { Link } from "react-router-dom";
import { BsClipboard2Heart } from "react-icons/bs";
import { Button } from "./ui/Button";
import SearchInput from "./SearchInput";

export default function Header() {
  return (
    <header className="flex justify-between items-center p-4 border-b border-gray-300">
      <Link to="/" className="flex items-center text-2xl">
        <BsClipboard2Heart className="text-brand" />
        <h1 className="ml-1 font-bold">Board</h1>
      </Link>
      <SearchInput />
      <Link to="/login">
        <Button text="Login" />
      </Link>
    </header>
  );
}
