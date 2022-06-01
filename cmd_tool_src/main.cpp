#include <iostream>

int main(int argc, char *argv[]) {
    std::cout << "Arg count: "<< argc << std::endl;

    for(int i = 0; i < argc; i++)
    {
        std::cout << "Arg at " << i << ": " << argv[i] << std::endl;
    }

    system("pause");

    return 0;
}
