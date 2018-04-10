/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.nuls.consensus.entity.validator.block.header;

import io.nuls.account.entity.Address;
import io.nuls.consensus.entity.block.BlockRoundData;
import io.nuls.consensus.entity.meeting.PocMeetingMember;
import io.nuls.consensus.entity.meeting.PocMeetingRound;
import io.nuls.consensus.manager.RoundManager;
import io.nuls.consensus.service.intf.BlockService;
import io.nuls.core.chain.entity.Block;
import io.nuls.core.chain.entity.BlockHeader;
import io.nuls.core.constant.ErrorCode;
import io.nuls.core.context.NulsContext;
import io.nuls.core.validate.NulsDataValidator;
import io.nuls.core.validate.ValidateResult;

/**
 * @author Niels
 * @date 2017/11/17
 */
public class HeaderPackerValidator implements NulsDataValidator<BlockHeader> {
    public static final HeaderPackerValidator INSTANCE = new HeaderPackerValidator();
    public RoundManager roundManager = RoundManager.getInstance();

    public BlockService blockService;

    private HeaderPackerValidator() {
    }

    public static HeaderPackerValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidateResult validate(BlockHeader header) {
        if (header.getHeight() == 0) {
            return ValidateResult.getSuccessResult();
        }
        BlockRoundData roundData = new BlockRoundData(header.getExtend());
        BlockRoundData preBlockRoundData;
        String preHash = header.getPreHash().getDigestHex();
        while (true) {
            Block preBlock = getBlockService().getBlock(preHash);
            if (null == preBlock) {
                return ValidateResult.getFailedResult(ErrorCode.ORPHAN_BLOCK, "pre block not exist!");
            }
            preBlockRoundData = new BlockRoundData(preBlock.getHeader().getExtend());
            preHash = preBlock.getHeader().getPreHash().getDigestHex();
            if (preBlockRoundData.getRoundIndex() > roundData.getRoundIndex()) {
                return ValidateResult.getFailedResult("block round calc wrong!");
            }
            if (preBlockRoundData.getRoundIndex() < roundData.getRoundIndex()) {
                break;
            }
        }
        PocMeetingRound round = roundManager.getRound(header,preBlockRoundData.getRoundIndex(), roundData.getRoundIndex(), true);

        if (null == round) {
            return ValidateResult.getFailedResult(ErrorCode.ORPHAN_BLOCK, "round is null");
        }
        PocMeetingRound roundTmp;
        for (long r = round.getIndex(); r>100; r --){
            roundTmp = round;
            if(roundTmp.getIndex() - 1 != roundTmp.getPreRound().getIndex() && roundTmp.getPreRound().getIndex() != 1){
                System.out.println("Round ERROR!!!!!!!!!!!!!!!!!!!!!");
            }
            roundTmp = roundTmp.getPreRound();
        }
        if (round.getStartTime() != roundData.getRoundStartTime()) {
            return ValidateResult.getFailedResult("round start time is not inconsistent!");
        }
        if (round.getMemberCount() != roundData.getConsensusMemberCount()) {
            return ValidateResult.getFailedResult("round member count is not inconsistent!");
        }
        PocMeetingMember member = round.getMember(roundData.getPackingIndexOfRound());
        if (member == null || !member.getPackingAddress().equals(Address.fromHashs(header.getPackingAddress()).getBase58())) {
            return ValidateResult.getFailedResult("round index is not inconsistent!");
        }
        return ValidateResult.getSuccessResult();
    }
    public BlockService getBlockService() {
        if (null == blockService) {
            blockService = NulsContext.getServiceBean(BlockService.class);
        }
        return blockService;
    }
}
