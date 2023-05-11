type ButtonProps = {
  text: string;
  onClick?: () => void;
};

export const Button = ({ text, onClick }: ButtonProps) => {
  return (
    <button
      className="px-4 py-2 text-white bg-brand rounded-sm hover:brightness-110"
      onClick={onClick}
    >
      {text}
    </button>
  );
};
