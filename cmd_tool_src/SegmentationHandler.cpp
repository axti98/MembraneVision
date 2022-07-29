#include "SegmentationHandler.h"

SegmentationHandler::SegmentationHandler(std::string* AImage_paths, int AImage_count) {
   image_paths = AImage_paths;
   image_count = AImage_count;
}

cv::Mat* SegmentationHandler::segmentation() {

    cv::Mat images[image_count];
    cv::Mat* threshholded_images = new cv::Mat[image_count];

    ///////////////////////////////////////////////////////
    // TODO: IF BACKGROUND IS TRANSPARENT, SEGMENTATION  //
    // WRONGLY INTERPRETES THE BACKGROUND AS THE IMGS    //
    ///////////////////////////////////////////////////////

    for(int i = 0; i < image_count; i++)
    {
        images[i] = cv::imread(image_paths[i], cv::IMREAD_COLOR);
        cv::cvtColor(images[i], images[i], cv::COLOR_BGR2GRAY);
        cv::threshold(images[i], threshholded_images[i], THRESH_VAL, THRESH_MAX,
                 cv::THRESH_BINARY_INV | cv::THRESH_OTSU);
    }

    return threshholded_images;
}

int** SegmentationHandler::neighbouring(const cv::Mat& AInputMat) {

    const int numbered_rows = NEIGHBOUR_MULTIPLIER(AInputMat.rows);
    const int numbered_cols = NEIGHBOUR_MULTIPLIER(AInputMat.cols);
    int number_count = 0;

    int** numbering = new int*[numbered_rows];
    for(int i = 0; i < numbered_rows; i++)
        numbering[i] = new int[numbered_cols];

    /////////////////////////////////////////////////
    ////////////// POSSIBLE BUG SOURCES /////////////
    // MAY NEED TO ITERATE FROM j = AInputMat.cols //
    /////////////////////////////////////////////////

    for(int i = 0; i < AInputMat.rows; i++) {
        for(int j = 0; j < AInputMat.cols; j++) {
            if (static_cast<int>(AInputMat.at<uchar>(i, j)) == IS_VOXEL)
            {
                numbering[LEFT_INDEX(i)][LEFT_INDEX(j)] = IS_VOXEL;
                numbering[LEFT_INDEX(i)][RIGHT_INDEX(j)] = IS_VOXEL;
                numbering[RIGHT_INDEX(i)][LEFT_INDEX(j)] = IS_VOXEL;
                numbering[RIGHT_INDEX(i)][RIGHT_INDEX(j)] = IS_VOXEL;
            } else {
                numbering[LEFT_INDEX(i)][LEFT_INDEX(j)] = IS_NOT_VOXEL;
                numbering[LEFT_INDEX(i)][RIGHT_INDEX(j)] = IS_NOT_VOXEL;
                numbering[RIGHT_INDEX(i)][LEFT_INDEX(j)] = IS_NOT_VOXEL;
                numbering[RIGHT_INDEX(i)][RIGHT_INDEX(j)] = IS_NOT_VOXEL;
            }
        }
    }

    for(int i = 0; i < numbered_rows; i++)
    {
        for(int j = 0; j < numbered_cols; j++)
        {
            if(numbering[i][j] == IS_VOXEL) numbering[i][j] = number_count++;
            std::printf("%5d", numbering[i][j]);
        }
        std::cout << std::endl;
    }

    int **neighbour_list = new int *[number_count];

    /*
     * NEIGHBOURS of (n, m):
     * (n − 1, m − 1) (n, m − 1) (n + 1, m − 1)
     *   (n − 1, m)     (n, m)     (n + 1, m)
     * (n − 1, m + 1) (n, m + 1) (n + 1, m + 1)
     */

    for(int n = 0; n < numbered_rows; n++)
    {
        for(int m = 0; m < numbered_cols; m++){
            int current_index = numbering[n][m];
            if(current_index == IS_NOT_VOXEL) continue;

            std::vector<int> temp;

            if(n < 1)
                temp.push_back(IS_NOT_VOXEL);
            else
                temp.push_back(numbering[n - 1][m]);

            if(n < 1 || m + 1 >= numbered_cols)
                temp.push_back(IS_NOT_VOXEL);
            else
                temp.push_back(numbering[n - 1][m + 1]);

            if(m + 1 >= numbered_cols)
                temp.push_back(IS_NOT_VOXEL);
            else
                temp.push_back(numbering[n][m + 1]);

            if(n + 1 >= numbered_rows || m + 1 >= numbered_cols)
                temp.push_back(IS_NOT_VOXEL);
            else
                temp.push_back(numbering[n + 1][m + 1]);

            if(n + 1 >= numbered_rows)
                temp.push_back(IS_NOT_VOXEL);
            else
                temp.push_back(numbering[n + 1][m]);

            if(n + 1 >= numbered_rows || m < 1)
                temp.push_back(IS_NOT_VOXEL);
            else
                temp.push_back(numbering[n + 1][m - 1]);

            if(m < 1)
                temp.push_back(IS_NOT_VOXEL);
            else
                temp.push_back(numbering[n][m - 1]);

            if(n < 1 || m < 1)
                temp.push_back(IS_NOT_VOXEL);
            else
                temp.push_back(numbering[n - 1][m - 1]);

            neighbour_list[current_index] = new int[temp.size()];
            //std::reverse(temp.begin(), temp.end());
            for(int x = 0; x < temp.size(); x++) {
                neighbour_list[current_index][x] = temp.at(x);
            }
        }
    }

    return neighbour_list;
}
