#include <iostream>
#include <fstream>
#include "Const.h"
#include <exception>
#include <string>
#include "SegmentationHandler.h"


class WrongArgException: public std::exception
{
    [[nodiscard]] const char* what() const noexcept override
    {
        return "Given arguments were invalid.";
    }
} wrongArgException;

int exit_code = CORRECT_EXIT_CODE;

using namespace cv;

int main(int argc, char *argv[]) {

    const int image_count = argc - ARR_POS_IMG_BEGIN;

    if (argc < MIN_REQU_ARGS){
        std::cout << "Not enough arguments passed to convert" << std::endl;
        exit_code = NO_ARG_EXIT_CODE;
        throw wrongArgException;
    }

    int expansion_degree = static_cast<int>(std::strtol(argv[ARR_POS_EXP_DEG], nullptr, DEFAULT_RADIX));
    int distance = static_cast<int>(std::strtol(argv[ARR_POS_DIS], nullptr, DEFAULT_RADIX));

    auto* image_paths = new std::string[image_count];
    std::cout << "Image count: " << image_count << std::endl;

    for(int i = ARR_POS_IMG_BEGIN; i < argc; i++)
    {
        image_paths[i - ARR_POS_IMG_BEGIN] = argv[i];
    }

    std::cout << "Expansion Degree: " << expansion_degree << "\n" << "Distance: " << distance << std::endl;
    for(int i = 0; i < image_count; i++)
    {
        std::cout << "Img " << i << ": " << image_paths[i] << std::endl;
    }

    SegmentationHandler sh(image_paths, image_count);
    sh.execute_segmentation();

    delete [] image_paths;
    return exit_code;
}