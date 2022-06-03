#include "SegmentationHandler.h"

SegmentationHandler::SegmentationHandler(std::string *AImage_paths, int AImage_count) {
   image_paths = AImage_paths;
   image_count = AImage_count;
}

std::string* SegmentationHandler::execute_segmentation() {

    cv::Mat images[image_count];
    for(int i = 0; i < image_count; i++)
    {
        images[i] = cv::imread(image_paths[i], cv::IMREAD_GRAYSCALE);
        cv::imwrite("test.png", images[i]);
    }

    return nullptr;
}

