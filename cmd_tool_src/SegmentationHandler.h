//
// Created by User on 03.06.2022.
//

#ifndef CMD_TOOL_SRC_SEGMENTATIONHANDLER_H
#define CMD_TOOL_SRC_SEGMENTATIONHANDLER_H

#include <iostream>

#include <opencv2/core.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>

#include "Const.h"

#define THRESH_MAX 1
#define THRESH_VAL 0


class SegmentationHandler{
private:
    int image_count;
    std::string* image_paths;
public:
    explicit SegmentationHandler(std::string* AImage_paths, int AImage_count);
    cv::Mat* execute_segmentation();
};

#endif //CMD_TOOL_SRC_SEGMENTATIONHANDLER_H
